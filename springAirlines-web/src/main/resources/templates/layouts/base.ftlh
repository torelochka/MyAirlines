<#macro main title css=[] scripts=[]>
    <!DOCTYPE html>
    <html lang="ru" xmlns="http://www.w3.org/1999/html">
    <head>
        <meta charset="UTF-8">
        <title>${title}</title>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="/css/bootstrap.min.css" type="text/css">
        <#list css as style>
            <link rel="stylesheet" type="text/css" href="/css/${style}">
        </#list>
    </head>
    <body>
    <#include "header.ftlh">
    <#nested>
    <#include "footer.ftlh">
    <script src="/js/jquery-3.3.1.min.js"></script>
    <script src="/js/bootstrap.min.js"></script>
    <#list scripts as script>
        <script src="/js/${script}"></script>
    </#list>
    </body>
    </html>
</#macro>