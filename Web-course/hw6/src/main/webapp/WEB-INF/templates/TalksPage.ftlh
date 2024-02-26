<#-- @ftlvariable name="talks" type="ru.itmo.wp.model.domain.Talk[]" -->
<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->
<#-- @ftlvariable name="text" type="java.lang.String" -->

<#import "commons.ftlh" as c>
<@c.page>
    <div class="talks">
        <div class="send-box">
            <div class="header">Send Message</div>
        </div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="send"/>
                <div class="field">
                    <div class="name">
                        <label for="targetId">Target user</label>
                    </div>
                    <div class="value">
                        <select name="targetId" id="targetId">
                            <#list users as user>
                                <option value="${user.id}">${user.login}</option>
                            </#list>
                        </select>
                    </div>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="text">Text</label>
                    </div>
                    <div class="value">
                        <textarea id="text" name="text" type="text" value="${text!}"></textarea>
                    </div>
                </div>
                <#if error??>
                    <div class="error">
                        ${error!}
                    </div>
                </#if>
                <div class="button-field">
                    <input type="submit" value="Send">
                </div>
            </form>
        </div>
        <div class="talksDatatable">
            <div class="caption">Talks</div>
            <table>
                <thead>
                <tr>
                    <th>Id</th>
                    <th>SourceUserId</th>
                    <th>TargetUserId</th>
                    <th>Text</th>
                    <th>CreationTime</th>
                </tr>
                </thead>
                <tbody>
                <#list talks as talk>
                    <tr>
                        <td>${talk.id}</td>
                        <td>${talk.sourceUserId}</td>
                        <td>${talk.targetUserId}</td>
                        <td>${talk.text}</td>
                        <td>${talk.creationTime}</td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>

</@c.page>