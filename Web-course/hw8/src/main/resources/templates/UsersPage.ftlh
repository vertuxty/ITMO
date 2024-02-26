<#import "commons.ftlh" as c>
<#import "/spring.ftl" as spring>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Created</th>
                <th>Action</th>
<#--                <#if user??>-->
<#--                    <th>Action</th>-->
<#--                </#if>-->
            </tr>
            </thead>
            <tbody>
            <#if users?size==0>
                <tr class="noData">
                    <td colspan="3">
                        No data
                    </td>
                </tr>
            <#else>
                <#list users as userLi>
                <tr>
                    <td class="id">${userLi.id}</td>
                    <td class="login">${userLi.login}</td>
                    <td class="creationTime">${userLi.creationTime}</td>
                    <td class="disabledForm">
                        <form method="post" action="">
                            <input type="hidden" value=${userLi.id?string} id="userId" name="userId"/>
                            <div class="button-field">
                                <#if userLi.disabled>
                                    <#assign stat="enable">
                                <#else>
                                    <#assign stat="disable">
                                </#if>
                                <input type="submit" value=${stat} id="disabled" name="disabled">
                            </div>
                        </form>
                    </td>
                </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
</@c.page>
