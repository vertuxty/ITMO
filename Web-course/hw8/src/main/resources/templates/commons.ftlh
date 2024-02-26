<#ftl auto_esc=true>
<#-- @ftlvariable name="notices" type="ru.itmo.wp.domain.Notice[]" -->
<#import "/spring.ftl" as spring>
<#macro page>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Codeforces</title>
        <link rel="stylesheet" type="text/css" href="<@spring.url '/css/normalize.css'/>">
        <link rel="stylesheet" type="text/css" href="<@spring.url '/css/style.css'/>">
        <link rel="stylesheet" type="text/css" href="<@spring.url '/css/form.css'/>">
        <link rel="stylesheet" type="text/css" href="<@spring.url '/css/datatable.css'/>">
        <link rel="stylesheet" type="text/css" href="<@spring.url '/css/block.css'/>">
        <script src="<@spring.url '/js/jquery-3.6.1.min.js'/>"></script>
        <script src="<@spring.url '/js/notify.min.js'/>"></script>
        <script src="<@spring.url '/js/app.js'/>"></script>
    </head>
    <body>
    <header>
        <a href="/"><img src="<@spring.url '/img/logo.png'/>" alt="Codeforces" title="Codeforces"/></a>
        <div class="languages">
            <a href="#"><img src="<@spring.url '/img/gb.png'/>" alt="In English" title="In English"/></a>
            <a href="#"><img src="<@spring.url '/img/ru.png'/>" alt="In Russian" title="In Russian"/></a>
        </div>
        <div class="enter-or-register-box">
            <#if user??>
                ${user.login}
                |
                <a href="<@spring.url '/logout'/>">Logout</a>
            <#else>
                <a href="<@spring.url '/enter'/>">Enter</a>
                |
                <a href="<@spring.url '/register'/>">Register</a>
            </#if>
        </div>
        <nav>
            <ul>
                <li><a href="<@spring.url '/'/>">Home</a></li>
                <li><a href="<@spring.url '/users/all'/>">Users</a></li>
                <li><a href="<@spring.url '/notices/create'/>">Notices</a></li>
            </ul>
        </nav>
    </header>
    <div class="middle">
        <aside>
            <#if notices??>
                <#list notices as notice>
                    <section>
                        <div class="header">
                            Notice id=${notice.id!}
                        </div>
                        <div class="body">
                            <#assign parts = notice.content?replace("<br>", "\r")?split("\r")/>
                            <#list parts as part>
                                <#assign partt = part?split("\n")!/>
<#--                                <p>${part}</p>-->
                                <#list partt as prt>
<#--                                    <#assign part = part?split("\n")/>-->
                                    <p>${prt!}</p>
                                </#list>
                            </#list>
                        </div>
                        <div class="footer">
                            <a href="#">View all</a>
                        </div>
                    </section>
                </#list>
<#--            </#macro>-->
            <#else>
                Notices are gone...
            </#if>
        </aside>
        <main>
            <#nested/>
        </main>
    </div>
    <footer>
        <a href="/">Ejudge</a> &copy; 2077 by Mike Mirzayanov
    </footer>
    <script>
        $(function () {
            <#if message??>
                notify("${message?js_string}");
            </#if>
        })
    </script>
    </body>
    </html>
</#macro>

<#macro error field>
    <@spring.bind field/>
    <#if spring.status.errorMessage??>
        <div class="error">${spring.status.errorMessage}</div>
    </#if>
</#macro>