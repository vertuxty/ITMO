<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#-- @ftlvariable name="handle" type="java.lang.String" -->

<#import "./common.ftlh" as c>

<@c.page>
    <#if user_id?has_content>
        <#assign u = c.findBy(users "id" user_id)!/>
    </#if>
    <#if u?has_content>
        <#assign checkPost = c.countOfPosts(posts "user_id" user_id)!/>
        <ol>
            <#if (checkPost != 0)>
                <#list posts?reverse as p>
                    <#if p.user_id == user_id>
                        <li><@c.postlist p true/></li>
                    </#if>
                </#list>
            <#else>
                User ${u.handle} does not have posts!
            </#if>
        </ol>
    <#else>
        No such user!
    </#if>
</@c.page>