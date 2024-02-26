<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#import "./common.ftlh" as c>

<@c.page>
    <#if handle?has_content>
        <#assign u = (c.findBy(users, "handle", handle!""))!/>
    <#else>
        <#assign u = (c.findBy(users, "id", user_id!-1))!/>
    </#if>

    <#if u?has_content>
        <#assign items=[
        {"name": "id", "value": u.id},
        {"name": "handle", "value": u.handle},
        {"name": "name", "value": u.name}
        ]/>
        <div class="user_list">
            <div class="info_block">
                <img alt="User_Avatar" title="User_Avatar" src="/img/no-title.png">
                <header>
                    <@c.userlink u false/>
                </header>
                <ul>
                    <#list items as item>
                        <li>
                            <p>${item.name}: ${item.value}</p>
                        </li>
                    </#list>
                    <li>
                        <p><@c.userPostsLink posts u/></p>
                    </li>
                </ul>
            </div>
            <div class="arrow">
                <div class="left">
                    <#if (users?seq_index_of(u) > 0)>
                        <@c.arrow_link_true users [users?seq_index_of(u) - 1] "⇽"/>
                    <#else>
                        <@c.arrow_link_false "⇽"/>
                    </#if>
                </div>
                <div class="right">
                    <#if (users?size - 1 > users?seq_index_of(u))>
                        <@c.arrow_link_true users [users?seq_index_of(u) + 1] "⇾"/>
                    <#else>
                        <@c.arrow_link_false "⇾"/>
                    </#if>
                </div>
            </div>
        </div>
    <#else>
        No such user
    </#if>
</@c.page>
