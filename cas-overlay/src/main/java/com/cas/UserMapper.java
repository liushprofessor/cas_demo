
package com.cas;

import org.apache.ibatis.annotations.Param;

/**
 * 功能： TODO(用一句话描述类的功能)
 *
 * ──────────────────────────────────────────
 *   version  变更日期    修改人    修改说明
 * ------------------------------------------
 *   V1.0.0   2020/1/10    Liush     初版
 * ──────────────────────────────────────────
 */

public interface UserMapper {


     String findUserName(@Param("username") String username, @Param("passowrd") String password);






}
