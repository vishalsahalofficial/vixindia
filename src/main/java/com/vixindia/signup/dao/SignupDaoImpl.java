package com.vixindia.signup.dao;

import com.vixindia.custom_exception.GlobalException;
import com.vixindia.login.service.OtpService;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class SignupDaoImpl implements SignupDao {

  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  @Autowired
  private OtpService otpService;
  private static final Logger APP_LOG = LoggerFactory.getLogger(SignupDaoImpl.class);

  @Override
  public Map<String, Object> getMobileVeificationOTPCount(Long mobileNumber) {
    try {
      String sql = "SELECT *,timestampdiff(MINUTE,mobile_otp_update_time,current_timestamp()) AS otp_life_time from user_verification where mobile_number =:mobileNumber";
      return jdbcTemplate.getJdbcTemplate()
          .queryForMap(sql, new MapSqlParameterSource().addValue("mobileNumber", mobileNumber));

    } catch (DataAccessException e) {
      return null;
    }
  }

  @Override
  public void updateMobileOtpCount(Long mobileNumber) {
    String resetTryCountSQL = "UPDATE user_verification SET mobile_otp_try_count=0, otp_sent_try_count=0, last_attempt=current_timestamp,mobile_otp_update_time=current_timestamp WHERE mobile_number =:mobileNumber";

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("mobileNumber", mobileNumber);

    jdbcTemplate.update(resetTryCountSQL, param);
  }

  @Override
  public void sendBorrowerMobileOtp(Long mobileNumber, String OTP) {
    String otpToken = OTP != null ? OTP : otpService.generateOtp();
    String isRowAlreadyExists = "select mobile_number from user_verification where mobile_number=:mobileNumber";
    String updateOtpSql =
        "update user_verification set mobile_otp=:otpToken,mobile_otp_update_time=current_timestamp(), "
            +
            " otp_sent_try_count=otp_sent_try_count+1 where mobile_number=:mobileNumber";
    String insertOtpSql =
        "insert into user_verification(mobile_number,mobile_otp,mobile_otp_update_time, " +
            " otp_sent_try_count)values(:mobileNumber,:otpToken,current_timestamp(), 1)";
//inserting
    try {

      SqlParameterSource params = new MapSqlParameterSource()
          .addValue("mobileNumber", mobileNumber)
          .addValue("otpToken", otpToken);

      jdbcTemplate.queryForObject(isRowAlreadyExists, params, String.class);
      jdbcTemplate.update(updateOtpSql, params);

    } catch (DataAccessException e) {

      SqlParameterSource params = new MapSqlParameterSource()
          .addValue("mobileNumber", mobileNumber)
          .addValue("otpToken", otpToken);

      jdbcTemplate.update(insertOtpSql, params);
    }
  }

  @Override
  public Map<String, Object> getOtpDetails(String mobileNo) {
    String SQL = "select * from user_verification as uv where mobile_number=:mobileNo and mobile_otp_update_time BETWEEN DATE_SUB(NOW(),INTERVAL 5 MINUTE) AND mobile_otp_update_time;";
    try {
      SqlParameterSource params = new MapSqlParameterSource().addValue("mobileNo", mobileNo);
      return jdbcTemplate.queryForMap(SQL, params);
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  @Override
  public void updateTryCount(String mobileNumber) {
    String sql1 = "SET SQL_SAFE_UPDATES=0;";
    String sql2 =
        "update user_verification set mobile_otp_try_count=mobile_otp_try_count+1 where mobile_number = '"
            + mobileNumber + "'";
    String sql3 = "SET SQL_SAFE_UPDATES=1;";
    jdbcTemplate.getJdbcTemplate().batchUpdate(sql1, sql2, sql3);
  }

  @Override
  public void setMobileVerified(String mobileNumber) {
    String sql1 = "SET SQL_SAFE_UPDATES=0;";
    String sql2 =
        "update user_verification set is_mobile_registered=1, is_otp_verified=1 where mobile_number='"
            + mobileNumber + "'";
    String sql3 = "SET SQL_SAFE_UPDATES=1;";
    jdbcTemplate.getJdbcTemplate().batchUpdate(sql1, sql2, sql3);
  }

  @Override
  public void updateResetTryCount(String mobileNumber) {
    String resetTryCountSQL = "UPDATE user_verification SET mobile_otp_try_count=0, otp_sent_try_count=0, last_attempt=current_timestamp,mobile_otp_update_time=current_timestamp WHERE mobile_number =:mobile_number";
    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("mobile_number", mobileNumber);
    jdbcTemplate.update(resetTryCountSQL, param);
  }

  @Override
  public void isMobileNumberAlreadyVerified(Long mobileNumber) {
    boolean isMobileVerified = false;
    try {
      String sql = "select is_mobile_registered from user_verification where mobile_number = '"
          + mobileNumber + "'";
//      var data = jdbcTemplate.getJdbcTemplate().queryForMap(sql);
      Map<String, Object> data = jdbcTemplate.getJdbcTemplate().queryForMap(sql);
      isMobileVerified = (boolean) data.getOrDefault("is_mobile_registered", false);

      if (isMobileVerified) {
        throw new GlobalException(HttpStatus.BAD_REQUEST.value(), "Already Verified",
            new ArrayList<>());
      }

    } catch (EmptyResultDataAccessException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public Long insertProfile(String name, Long mobileNumber) {
    String SQL = "insert into user_profile (name,mobile_number,is_mobile_verified) VALUES (:name, :mobile_number, :is_mobile_verified)";
    try {
      SqlParameterSource params = new MapSqlParameterSource()
          .addValue("name", name)
          .addValue("mobile_number", mobileNumber)
          .addValue("is_mobile_verified", 1);

      KeyHolder keyHolder = new GeneratedKeyHolder();
      jdbcTemplate.update(SQL, params, keyHolder);

      return Objects.requireNonNull(keyHolder.getKey(),
              "User creation failed due either Mobile number not verified or database issues")
          .longValue();
    } catch (DataAccessException e) {
      return null;
    }
  }

}
