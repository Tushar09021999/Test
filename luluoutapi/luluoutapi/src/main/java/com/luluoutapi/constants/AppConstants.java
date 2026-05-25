package com.luluoutapi.constants;

import java.util.ResourceBundle;

public class AppConstants {

    static ResourceBundle commonbundle = ResourceBundle.getBundle("api-config");

    final static public String UPD_ACC_URL = commonbundle.getString("update.acc.url");
    final static public String CLOSE_ACC_URL = commonbundle.getString("close.acc.url");
    final static public String ADD_ACC_URL = commonbundle.getString("add.acc.url");
    final static public String DELETE_DLT_ACC_URL =commonbundle.getString("delete.DLT.acc.url");
    final static public String RQST_STATUS_URL = commonbundle.getString("request.status.url");

    final static public String UPD_ACC_QUERY = "SELECT REQUEST_ID,REASON_CODE,FLD001,FLD002,FLD003,FLD004,FLD005,FLD006,FLD007,FLD008 from out_pam_api where STATUS=? and API_KEY=?";
    final static public String CLOSE_ACC_QUERY = "SELECT REQUEST_ID,FLD001 from out_pam_api where STATUS=? and API_KEY=?";
    final static public String ADD_ACC_QUERY = "SELECT REQUEST_ID,FLD001,FLD002,FLD003,FLD004 from out_pam_api where STATUS=? and API_KEY=?";
    final static public String DELETE_ACC_QUERY = "SELECT REQUEST_ID,FLD001 from out_pam_api where STATUS=? and API_KEY=?";
    final static public String RQST_STATUS_QUERY = "SELECT  REQUEST_ID from out_pam_api where STATUS=? and API_KEY=?";

    final static public String ADACC = "ADACC";
    final static public String CLSACC = "CLSACC";
    final static public String DLTACC = "DLTACC";
    final static public String REQSTS = "REQSTS";
    final static public String UPDACC = "UPDACC";

    final static public String REQUEST_ID = "REQUEST_ID";
    final static public String REASON_CODE = "REASON_CODE";
    final static public String API_KEY = "API_KEY";
    final static public String FLD001 = "FLD001";
    final static public String FLD002 = "FLD002";
    final static public String FLD003 = "FLD003";
    final static public String FLD004 = "FLD004";
    final static public String FLD005 = "FLD005";
    final static public String FLD006 = "FLD006";
    final static public String FLD007 = "FLD007";
    final static public String FLD008 = "FLD008";
    final static public String FLD009 = "FLD009";
    final static public String FLD010 = "FLD010";
    final static public String FLD011 = "FLD011";
    final static public String FLD012 = "FLD012";
    final static public String FLD013 = "FLD013";
    final static public String FLD014 = "FLD014";
    final static public String FLD015 = "FLD015";
    final static public String FLD016 = "FLD016";
    final static public String FLD017 = "FLD017";
    final static public String FLD018 = "FLD018";
    final static public String FLD019 = "FLD019";
    final static public String FLD020 = "FLD020";
    final static public String FLD021 = "FLD021";
    final static public String FLD022 = "FLD022";
    final static public String FLD023 = "FLD023";
    final static public String FLD024 = "FLD024";
    final static public String FLD025 = "FLD025";
    final static public String FLD026 = "FLD026";
    final static public String FLD027 = "FLD027";
    final static public String FLD028 = "FLD028";
    final static public String FLD029 = "FLD029";
    final static public String FLD030 = "FLD030";
    final static public String FLD031 = "FLD031";
    final static public String FLD032 = "FLD032";
    final static public String FLD033 = "FLD033";
    final static public String FLD034 = "FLD034";
    final static public String FLD035 = "FLD035";
    final static public String FLD036 = "FLD036";
    final static public String FLD037 = "FLD037";
    final static public String FLD038 = "FLD038";
    final static public String FLD039 = "FLD039";
    final static public String FLD040 = "FLD040";

}
