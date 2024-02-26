<#-- @ftlvariable name="noticeContent" type="ru.itmo.wp.form.NoticeCredentials" -->
<#import "commons.ftlh" as c>
<#import "/spring.ftl" as spring>
<@c.page>
    <div class="notice form-box">
        <div class="header">AddNotice</div>
        <div class="body">
            <form method="post" action="">
                <div class="field">
                    <div class="name">
                        <label for="content">Content</label>
                    </div>
                    <div class="value">
                        <textarea id="content" name="content"></textarea>
                    </div>
                    <@c.error "noticeContent.content"/>
                </div>
                <div class="button-field">
                    <input type="submit" value="Add">
                </div>
            </form>
        </div>
    </div>
</@c.page>