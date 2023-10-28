package com.vixindia.login.dao;

import com.vixindia.custom_exception.GlobalException;
import com.vixindia.login.dto.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;
  private static final Logger APP_LOG = LoggerFactory.getLogger(UserDaoImpl.class);

  @Override
  public User findByMobileNumber(String mobileNumber) {
    try {
      String sql = "SELECT * FROM user_profile WHERE mobile_number = :mobileNumber";

      SqlParameterSource namedParameters = new MapSqlParameterSource("mobileNumber", mobileNumber);

      return jdbcTemplate.queryForObject(sql, namedParameters, new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
          return User.builder()
              .id(rs.getInt("id"))
              .mobileNumber(rs.getString("mobile_number"))
              .otp(rs.getString("otp"))
              .otpTryCount(rs.getInt("otp_try_count"))
              .build();
        }
      });
    } catch (DataAccessException e) {
      return null;
    }
  }
  @Override
  public void updateOtp(String mobileNumber, String otp) {
    String sql = "UPDATE user_profile SET otp =:otp WHERE mobile_number =:mobile_number";
    MapSqlParameterSource params = new MapSqlParameterSource().addValue("otp", otp)
        .addValue("mobile_number", mobileNumber);
    jdbcTemplate.update(sql, params);
  }

  @Override
  public void updateToken(String token, String mobileNumber) {
    String sql = "UPDATE user_profile SET token =:token WHERE mobile_number =:mobile_number";
    MapSqlParameterSource params = new MapSqlParameterSource().addValue("token", token)
        .addValue("mobile_number", mobileNumber);
    jdbcTemplate.update(sql, params);
  }

  @Override
  public Boolean isNeedToSendOTP(String mobileNumber) {
    String SQL =
        "SELECT otp_try_count, timestampdiff(MINUTE,mobile_otp_update_time,current_timestamp()) AS otp_life_time FROM user_profile "
            +
            " WHERE mobile_number =:mobileNumber ";
    try {
      SqlParameterSource parameterSource = new MapSqlParameterSource()
          .addValue("mobileNumber", mobileNumber);

      Map<String, Object> resultMap = jdbcTemplate.queryForMap(SQL, parameterSource);
      Integer tryCount = Integer.parseInt(resultMap.get("otp_try_count").toString());
      Integer OTPLifeTime = Integer.parseInt(resultMap.get("otp_life_time").toString());

      if (tryCount >= 3 && OTPLifeTime < 5) {
        APP_LOG.info(
            "OTP IS NOT SENT BECAUSE OTP TRY COUNT IS " + tryCount + "  AND OTP LIFE TIME IS "
                + OTPLifeTime + " MIN.");
        return Boolean.FALSE;
      }
      if (OTPLifeTime > 5) {
        APP_LOG.info("resseting count ");
        resetTryCount(mobileNumber);
        return Boolean.TRUE;
      }
      return Boolean.TRUE;
    } catch (EmptyResultDataAccessException e) {
      throw new GlobalException(HttpStatus.BAD_REQUEST.value(), "Mobile number is not registered",
          new ArrayList<>());
    } catch (Exception e) {
      throw new GlobalException(HttpStatus.BAD_REQUEST.value(), "Something Went Wrong",
          new ArrayList<>());
    }
  }

  @Override
  public void updateOtpTryCountAndVerification(String mobileNumber, Integer tryCount,
      Integer isMobileVerified) {
    String SQL = "UPDATE user_profile SET otp_try_count=:tryCount, is_mobile_verified =:isMobileVerified WHERE mobile_number =:mobileNumber ";
    String SQL_ONLY_FOR_TRYCOUNT = "UPDATE user_profile SET otp_try_count=:tryCount WHERE mobile_number =:mobileNumber ";
    try {
      MapSqlParameterSource paramMap = new MapSqlParameterSource()
          .addValue("mobileNumber", mobileNumber)
          .addValue("tryCount", tryCount)
          .addValue("isMobileVerified", isMobileVerified);
      APP_LOG.info("isMobileVerified is " + isMobileVerified);
      if (isMobileVerified == null) {

        jdbcTemplate.update(SQL_ONLY_FOR_TRYCOUNT, paramMap);
      } else {
        jdbcTemplate.update(SQL, paramMap);
      }
    } catch (Exception e) {
      throw new GlobalException(HttpStatus.BAD_REQUEST.value(),
          "Something Went Wrong While updateOtpTryCountAndVerification", new ArrayList<>());
    }
  }

  private void resetTryCount(String mobileNumber) {
    String SQL = "UPDATE user_profile SET otp_try_count=0, mobile_otp_update_time=current_timestamp WHERE mobile_number =:mobileNumber ";
    try {
      SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("mobileNumber",
          mobileNumber);
      jdbcTemplate.update(SQL, parameterSource);
    } catch (Exception e) {
      throw new GlobalException(HttpStatus.BAD_REQUEST.value(),
          "Something Went Wrong While Resetting OTP try count", new ArrayList<>());
    }
  }


}
