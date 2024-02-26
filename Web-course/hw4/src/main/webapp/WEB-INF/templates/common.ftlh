<#macro page>
    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <title>Codeforces</title>
        <link rel="stylesheet" href="/css/normalize.css">
        <link rel="stylesheet" href="/css/style.css">
        <link rel="stylesheet" href="/css/userBlock.css">
        <link rel="icon" href="/favicon.ico">
    </head>
    <body>
    <@header/>
    <div class="middle">
        <@sidebar/>
        <main>
            <#nested/>
        </main>
    </div>
    <@footer/>
    </body>
    </html>
</#macro>

<#macro header>
    <header>
        <a href="#" class="logo"><img alt="Codeforces" title="Codeforces"
                                      src="/img/codeforces-logo-with-telegram.png"></a>
        <div class="languages">
            <a href="#"><img alt="In English" title="In English"
                             src="/img/gb.png"></a>
            <a href="#"><img alt="In Russian" title="In Russian"
                             src="/img/ru.png"></a>
        </div>
        <div class="enter-or-register-box">
            <#if user??>
                <@userlink user true/>
                |
                <a href="/logout">Logout</a>
            <#else>
                <a href="/enter">Enter</a>
                |
                <a href="/register">Register</a>
            </#if>
        </div>
        <nav>
            <#assign items=[
            {"href": "/index", "name": "Home"},
            {"href": "/misc/help", "name": "Help"},
            {"href": "/contests", "name": "Contests"},
            {"href": "/users", "name": "Users"}
            ]/>
            <ul>
                <#list items as item>
                    <li>
                        <#if item.href == menu_page>
                            <a class="underline_on" href="${item.href}">${item.name}</a>
                        <#else>
                            <a href="${item.href}">${item.name}</a>
                        </#if>
                    </li>
                </#list>
            </ul>
        </nav>
    </header>
</#macro>

<#macro sidebar>
    <aside>
        <#list posts as item>
            <section>
                <div class="header">Post #${item.id}</div>
                <div class="body">
                    ${reduceText(item.text)}
                </div>
                <div class="footer">
                    <@postlink item/>
                </div>
            </section>
        </#list>
    </aside>
</#macro>

<#macro footer>
    <footer>
        2010-2021 &copy; by Mike Mirzayanov
    </footer>
</#macro>

<#macro userlink user nameOnly>
<#-- @ftlvariable name="user" type="ru.itmo.web.hw4.model.User" -->
    <#if nameOnly>
        <a href="/user?handle=${user.handle}">${user.name}</a>
    <#else>
        <a class=${user.color.name()} href="/user?handle=${user.handle}">${user.name}</a>
    </#if>
</#macro>

<#macro userlinkhandle user nameOnly>
<#-- @ftlvariable name="user" type="ru.itmo.web.hw4.model.User" -->
    <#if nameOnly == true>
        <a href="/user?handle=${user.handle}">${user.handle}</a>
    <#else>
        <a class="${user.color.name()}" href="/user?handle=${user.handle}">${user.handle}</a>
    </#if>
</#macro>

<#macro userlinkUser user text>
<#-- @ftlvariable name="user" type="ru.itmo.web.hw4.model.User" -->
    <a href="/user?handle=${user.handle}"> ${text} </a>
</#macro>

<#macro arrow_link_true user arrow>
    <div class="link_true">
        <@userlinkUser user arrow/>
    </div>
</#macro>

<#macro arrow_link_false arrow>
    <div class="link_false">
        ${arrow}
    </div>
</#macro>

<#macro postlist post do_reduce>
    <#assign u = (findBy(users "id" post.user_id))/>
    <article>
        <div class="title">${post.title?cap_first}</div>
        <#if u?has_content>
            <div class="information">By ${u.name}, ${post.creationTime}</div>
        <#else>
            <div class="information">Can't find user that write this Post...</div>
        </#if>
        <div class="body">
            <#if do_reduce>
                <p>${reduceText(post.text)}</p>
            <#else>
                <p>${post.text}</p>
            </#if>
        </div>
        <ul class="attachment">
            <li>Link to user_profile <@userlink u false/></li>
        </ul>
        <@footerAttach post/>
    </article>
</#macro>

<#macro footerAttach post>
    <div class="footer">
        <div class="left">
            <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
            <span class="positive-score">+173</span>
            <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
        </div>
        <div class="right">
            <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
            ${post.creationTime}
            <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
            <a href="#">68</a>
        </div>
    </div>
</#macro>

<#macro postlink post>
    <a href="/post?post_id=${post.id}">View all</a>
</#macro>

<#macro userPostsLink posts u>
    <a href="/posts?user_id=${u.id}"> Posts: ${countOfPosts(posts "user_id" u.id)} </a>
</#macro>

<#macro user_thead>
    <thead>
        <tr>
            <th>Id</th>
            <th>Created</th>
            <th>User</th>
            <th>Name</th>
            <th>Test</th>
        </tr>
    </thead>
</#macro>


<#function reduceText text>
    <#if (text?length > 350)>
        <#return text?substring(0, 350) + "..."/>
    <#else>
        <#return text/>
    </#if>
</#function>

<#function countOfPosts items key value>
    <#local count = 0/>
    <#list items as item>
        <#if item[key] == value>
            <#local count++/>
        </#if>
    </#list>
    <#return count/>
</#function>

<#function findBy items key value>
    <#list items as item>
        <#if item[key] == value>
            <#return item/>
        </#if>
    </#list>
</#function>
