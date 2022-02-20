from tkinter import *  
import subprocess  
import shlex
import os
from datetime import datetime

#JavaScript
  
def anyStr(s):
    if (len(s) == 0):
        return " "
    else:
        return s

TEMPL = """
[//]: # (title)
## {title}

[//]: # (author)
{author}

[//]: # (dsc)
{description}

[//]: # (tags)
{tags}

[//]: # (img)
[<img src="img.png" width="150"/>](img.png)

[//]: # (url)
{url}

[//]: # (content)
{content}

[//]: # (indexed=false)
[//]: # (changed=false)
[//]: # (date={date})
[//]: # (date +%F_%T)
"""

def saveForm():  
    time = datetime.now()
    #2022-02-15_12:41:41
    date = time.strftime("%Y-%m-%d_%H:%M:%S")
    ndird = time.strftime("%Y%m/%d")
    hms = time.strftime("%H%M%S")
    ndir = ndird + "/" + hms
    os.makedirs(ndir, exist_ok=True)
    outFile = ndir + "/index.md"

    content = txtContent.get()
    contentStr = content
    if (len(content) > 0):
        contentStr = "[{}]({})".format(content, content)

    url=txtUrl.get()
    urlStr = url
    if (len(url) > 0):
        urlStr = "[{}]({})".format(url, url)
    
    tags=txtTags.get()
    tagsArr = tags.split()
    tagsArrLen = len(tagsArr)
    for i in range(tagsArrLen):
        tagsArr[i] = "- " + tagsArr[i] + "  "

    tagsStr = "\n".join(tagsArr)

    title=txtTitle.get()
    author=txtAuthor.get()
    dsc=txtDescription.get("1.0", END)
    cmdName="dolphin"
    lblResult.configure(text=outFile)  
    #dolphin $ndir
    cmdarr = [
        cmdName,
        ndir
        ]
    print(cmdarr)
    ##subprocess.run(["ls", "-l"])
    templ = TEMPL
    #print(templ.format(title = title, author = author, description = dsc, tags = tagsStr, url = urlStr, content = contentStr, date = date))
    print(outFile)
    f = open(outFile, "w")
    f.write(templ.format(title = title, author = author, description = dsc, tags = tagsStr, url = urlStr, content = contentStr, date = date))
    f.close()
    subprocess.run(cmdarr)
  
def clearForm():
    txtUrl.delete(0, END)
    txtContent.delete(0, END)
    txtTags.delete(0, END)
    txtTitle.delete(0, END)
    txtAuthor.delete(0, END)
    txtDescription.delete("1.0", END)

window = Tk()  
window.title("Создать описание ссылки или файла")  
window.geometry('1024x400')

wd = 40

row = 0
lblUrl = Label(window, text="Url")
lblUrl.grid(column=0, row=row)  
txtUrl = Entry(window,width=wd)  
txtUrl.grid(column=1, row=row)  

row += 1
lblContent = Label(window, text="Content filename")
lblContent.grid(column=0, row=row)  
txtContent = Entry(window,width=wd)  
txtContent.grid(column=1, row=row)  

row += 1
lblTags = Label(window, text="Tags (space separated)")
lblTags.grid(column=0, row=row)  
txtTags = Entry(window,width=wd)  
txtTags.grid(column=1, row=row)  

row += 1
lblTitle = Label(window, text="Title")
lblTitle.grid(column=0, row=row)  
txtTitle = Entry(window,width=wd)  
txtTitle.grid(column=1, row=row)  

row += 1
lblAuthor = Label(window, text="Author")
lblAuthor.grid(column=0, row=row)  
txtAuthor = Entry(window,width=wd)  
txtAuthor.grid(column=1, row=row)  

row += 1
lblDescription = Label(window, text="Description")
lblDescription.grid(column=0, row=row)  
txtDescription = Text(window, height=8)  
txtDescription.grid(column=1, row=row)  

row += 1
lblResult = Label(window, text="Result")
lblResult.grid(column=1, row=row)  

row += 1
btnClear = Button(window, text="Clear", command=clearForm)  
btnClear.grid(column=0, row=row)  
btnSave = Button(window, text="Save", command=saveForm)  
btnSave.grid(column=1, row=row)  

window.mainloop()
