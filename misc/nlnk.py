import tkinter as tk
import subprocess  
import shlex
import os
from datetime import datetime
import urllib.parse

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
        contentStr = "[{}]({})".format(content.replace('[','').replace(']',''), urllib.parse.quote_plus(content))

    url=txtUrl.get()
    urlStr = url
    if (len(url) > 0):
        urlStr = "[{}]({})".format(url, url)
    
    tagsArr = [txtTags.get(idx) for idx in txtTags.curselection()]
    
    tagsArrLen = len(tagsArr)
    for i in range(tagsArrLen):
        tagsArr[i] = "- " + tagsArr[i] + "  "

    tagsStr = "\n".join(tagsArr)

    title=txtTitle.get()
    author=txtAuthor.get()
    dsc=txtDescription.get("1.0", tk.END)
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
    #subprocess.run(cmdarr)
  
def clearForm():
    txtUrl.delete(0, tk.END)
    txtContent.delete(0, tk.END)
    txtTags.delete(0, tk.END)
    txtTitle.delete(0, tk.END)
    txtAuthor.delete(0, tk.END)
    txtDescription.delete("1.0", tk.END)

window = tk.Tk()  
window.title("Создать описание ссылки или файла")  
window.geometry('1024x800')

wd = 40

row = 0
lblUrl = tk.Label(window, text="Url")
lblUrl.grid(column=0, row=row)  
txtUrl = tk.Entry(window,width=wd)  
txtUrl.grid(column=1, row=row)  

row += 1
lblContent = tk.Label(window, text="Content filename")
lblContent.grid(column=0, row=row)  
txtContent = tk.Entry(window,width=wd)  
txtContent.grid(column=1, row=row)  

row += 1

lblTags = tk.Label(window, text="Tags (space separated)")
lblTags.grid(column=0, row=row)  
txtTags = tk.Listbox(window, width=wd, selectmode="extended")
txtTags.grid(column=1, row=row)  
for i in ("css", 
    "dog", 
    "git", 
    "html", 
    "Java", 
    "JavaScript", 
    "music", 
    "node", 
    "patterns", 
    "programming", 
    "React", 
    "TypeScript", 
    "география", 
    "музыка", 
    "физика", 
    "флейта", 
    "фортепиано", 
    "химия", 
    "школа", 
    "экономика"):
    txtTags.insert(tk.END, i)

row += 1
lblTitle = tk.Label(window, text="Title")
lblTitle.grid(column=0, row=row)  
txtTitle = tk.Entry(window,width=wd)  
txtTitle.grid(column=1, row=row)  

row += 1
lblAuthor = tk.Label(window, text="Author")
lblAuthor.grid(column=0, row=row)  
txtAuthor = tk.Entry(window,width=wd)  
txtAuthor.grid(column=1, row=row)  

row += 1
lblDescription = tk.Label(window, text="Description")
lblDescription.grid(column=0, row=row)  
txtDescription = tk.Text(window, height=8)  
txtDescription.grid(column=1, row=row)  

row += 1
lblResult = tk.Label(window, text="Result")
lblResult.grid(column=1, row=row)  

row += 1
btnClear = tk.Button(window, text="Clear", command=clearForm)  
btnClear.grid(column=0, row=row)  
btnSave = tk.Button(window, text="Save", command=saveForm)  
btnSave.grid(column=1, row=row)  

window.mainloop()
