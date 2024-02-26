<#import "commons.ftlh" as c/>
<#--<#import "/spring.ftl" as spring/>-->

<@c.page>
<#--    <#list post as post>-->
    <#if postById??>
        <@c.post post=postById/>
        <#if user??>
            <div class="form">
                <div class="header">Write Comment</div>
                <div class="body">
                    <form method="post" action="">
                        <input type="hidden" name="action" value="writeComment"/>
                        <div class="field">
                            <div class="name">
                                <label for="text">Text</label>
                            </div>
                            <div class="value">
                                <textarea id="text" name="text">${comment.text!}</textarea>
                            </div>
                            <@c.error "comment.text"/>
                        </div>
                        <div class="button-field">
                            <input type="submit" value="Write">
                        </div>
                    </form>
                </div>
            </div>
        </#if>
        <#if postById.comments?? && (postById.comments?size &gt; 0)>
            <div class="comments">
                <#list postById.comments as currComment>
                    <@c.comment currComment/>
                </#list>
            </div>
        <#else>
            <div class="no-Data-comments">
                No comments!!!!!!!!!!!!!!
            </div>
        </#if>
    <#else >
        No such post!
    </#if>
<#--    </#list>-->
</@c.page>