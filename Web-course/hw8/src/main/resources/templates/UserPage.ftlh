<#-- @ftlvariable name="userPageId" type="ru.itmo.wp.domain.User" -->
<#import "/spring.ftl" as spring>
<#import "commons.ftlh" as c>
<@c.page>
    <#if userPageId?has_content>
        <#assign items=[
        {"name": "id", "value": userPageId.id},
        {"name": "login", "value": userPageId.login},
        {"name": "creationTime", "value": userPageId.creationTime}
<#--        {"name": "disabled", "value": userPageId.disabled}-->
        ]/>
        <div class="user_list block-data">
            <div class="info_block">
                <header>
                    ${userPageId.login}
                </header>
                <ul>
                    <#list items as item>
                        <li>
                            <p>${item.name}: ${item.value}</p>
                        </li>
                    </#list>
                </ul>
            </div>
        </div>
    <#else>
        No such user
    </#if>
</@c.page>