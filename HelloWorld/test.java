package com.ej.alumni.wordpress;
public abstract class WordpressConstants {
  public static final String USERNAME = "username";
  public static final String EMAIL = "email";
  public static final String FIRST_NAME = "first_name";
  public static final String LAST_NAME = "last_name";
  public static final String NICKNAME = "nickname";
  public static final String DISPLAY_NAME = "display_name";
  public static final String ALUMNI_SECURITY_PARAMETER = "alumni_register";
  public static final String ALUMNI_USER_ID = "alumni_user_id";
  public static final String ALUMNI_SAP_USER_ID = "alumni_sap_user_id";
  public static final String ALUMNI_TOKEN = "alumni_token";
  public static final String ALUMNI_NETWORK = "alumni_network";
  public static final String NOTIFY = "notify";
  public static final String NONCE = "nonce";
  public static final String COOKIE = "cookie";
  public static final String CONTROLLER = "controller";
  public static final String METHOD = "method";
  public static final String GET = "get";
  public static final String POST = "post";
  public static final String PUT = "put";
  public static final String STATUS = "status";
  public static final String SLUG = "slug";
  public static final String PAGE = "page";
  public static final String COUNT = "count";
  public static final String EXTRAQUERY = "extraquery";
  public static final String CATEGORY_NAME = "category_name";
  public static final String META_KEY = "meta_key";
  public static final String META_COMPARE = "meta_compare";
  public static final String POST_ID = "post_id";
  public static final String PARAM_ORDER_BY = "orderby";
  // API ENDPOINTS
  public static final String GET_CATEGORY_INDEX = "get_category_index/";
  public static final String USER_REGISTER = "user/register/";
  public static final String GET_RECENT_POSTS = "get_recent_posts/";
  public static final String GET_POSTS = "get_posts/";
  public static final String SAVE_ALUMNI_STORY = "alumnistory/save_alumni_story/";
  public static final String GET_ALUMNI_STORY = "alumnistory/get_alumni_story/";
  public static final String CREATE_POST = "posts/create_post/";
  public static final String POST_COMMENT = "user/post_comment/";
  public static final String GENERATE_ALUMNI_TOKEN = "auth/generate_alumni_auth/";
  public static final String GET_NONCE = "get_nonce/";
  public static final String SAVE_CATEGORY = "save_category/";
  public static final String SAVE_ALUMNI_NETWORK = "alumnistory/save_alumni_network/";
  // Messages
  public static final String ERR_MSG_FORBIDDEN = "Action is forbidden";
  public static final String ARTICLE_NOT_EXIST =
      "the article doesn't exist or you don't have permissions";
  public static final String COMMENT_PERMISSIONS =
      "you don't have permissions to write comments in that article";
  public static final String SAVE_ALUMNI_STORY_PERMISSIONS =
      "you don't have permissions to change in that story";
}
