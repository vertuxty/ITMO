
<#import "./common.ftlh" as c>

<@c.page>
    <div class="datatable">
        <div class="caption">Submissions</div>
        <table>
            <@c.user_thead/>
            <tbody>
            <#list users as item>
                <#assign userHandle>
                    <@c.userlinkhandle item false/>
                </#assign>
                <#assign userLink>
                    <@c.userlink item false/>
                </#assign>
                <#assign items = [
                {"name": "id", "value": item.id},
                {"name": "time", "value": "2018-09-10 12:32:11"},
                {"name": "handle", "value": userHandle},
                {"name": "name", "value": userLink},
                {"name": "test", "value": 381}
                ]/>
                <tr>
                    <#list items as it>
                        <td>${it.value}</td>
                    </#list>
                </tr>
<#--                <tr>-->
<#--                    <td>${item.id}</td>-->
<#--                    <td>2018-09-10 12:32:11</td>-->
<#--                    <td><@c.userlinkhandle item false/></td>-->
<#--                    <td><@c.userlink item false/></td>-->
<#--                    <td>391</td>-->
<#--                </tr>-->
            <#else>
                No one was registered yet... :(
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>