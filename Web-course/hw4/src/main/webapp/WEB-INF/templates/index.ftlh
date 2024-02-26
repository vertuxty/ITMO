<#import "./common.ftlh" as c>

<@c.page>
    <ol>
        <#list posts?reverse as p>
            <li>
                <@c.postlist p true/>
            </li>
        </#list>
    </ol>
</@c.page>
