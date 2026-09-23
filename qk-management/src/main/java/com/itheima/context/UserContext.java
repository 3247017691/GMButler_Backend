package com.itheima.context;

/**
 * 当前登录用户上下文，由 LoginInterceptor 在请求开始时填充、结束时清理
 */
public class UserContext {

    /**
     * 管理员角色标签
     */
    public static final String ROLE_ADMIN = "admin";

    private static final ThreadLocal<CurrentUser> HOLDER = new ThreadLocal<>();

    public static void set(CurrentUser user) {
        HOLDER.set(user);
    }

    public static CurrentUser get() {
        return HOLDER.get();
    }

    public static Integer getId() {
        CurrentUser user = get();
        return user == null ? null : user.getId();
    }

    /**
     * 当前用户是否为管理员
     */
    public static boolean isAdmin() {
        CurrentUser user = get();
        return user != null && ROLE_ADMIN.equals(user.getRoleLabel());
    }

    public static void clear() {
        HOLDER.remove();
    }

    /**
     * 当前登录用户
     */
    public static class CurrentUser {
        private Integer id;
        private String username;
        private String roleLabel;

        public CurrentUser(Integer id, String username, String roleLabel) {
            this.id = id;
            this.username = username;
            this.roleLabel = roleLabel;
        }

        public Integer getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

        public String getRoleLabel() {
            return roleLabel;
        }
    }
}
