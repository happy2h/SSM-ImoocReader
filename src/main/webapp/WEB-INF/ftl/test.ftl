<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="/resources/wangEditor.min.js"></script>

</head>
<body>

    <div>
        <button id="btnRead">读取内容</button>
        <button id="btnWrite">写入内容</button>
    </div>
    <div id="divEditor" style="width: 800px; height: 600px">
    <script>
        var E = window.wangEditor;
        var editor = new E("#divEditor");
        editor.create();
        document.getElementById("btnRead").onclick = function (){
            var content = editor.txt.html();
            alert(content)
        }
        document.getElementById("btnWrite").onclick = function (){
            var content = "<li style='color:red'> 我是<b>新内容</b></li>";
            editor.txt.html(content);
        }
    </script>
</div>
</body>
</html>