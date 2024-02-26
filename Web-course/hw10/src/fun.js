export const commentsCount = function (post, comments) {
    return postComments(post, comments).length;
}

export const postComments = function (post, comments) {
    return Object.values(comments).filter(c => c.postId === post.id);
}