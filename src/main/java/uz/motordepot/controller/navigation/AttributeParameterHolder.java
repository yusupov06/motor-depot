package uz.motordepot.controller.navigation;

/**
 * {@link AttributeParameterHolder} => final class for storing all attributes and parameters
 */
public final class AttributeParameterHolder {

    /**
     * command => command main request param for command which client requests
     */
    public static final String PARAMETER_COMMAND = "command";

    public static final String SESSION_ATTRIBUTE_CURRENT_PAGE = "current_page";
    public static final String SESSION_ATTRIBUTE_CURRENT_COMMAND = "current_command";

    /**
     * SESSION_ATTRIBUTE_EDITING => session attribute for set as session attribute for knowing which is editing
     */
    public static final String SESSION_ATTRIBUTE_EDITING = "editing";
    public static final String SESSION_ATTRIBUTE_CURRENT_USER = "current_user";

    /**
     * SESSION_ATTR_PAGE => resultPage -> session attribute for return page of items as result
     */
    public static final String SESSION_ATTR_PAGE = "resultPage";

    /**
     * when adding cruise we need pagination for requests and drivers for choosing
     * <p>
     * SESSION_CRUISE_REQUEST_PAGE => for requests
     * SESSION_CRUISE_DRIVER_PAGE => for drivers
     */
    public static final String SESSION_CRUISE_REQUEST_PAGE = "requestsPage";
    public static final String SESSION_CRUISE_DRIVER_PAGE = "driversPage";

    /**
     * ADDING Cruises page we need pagination two of below are for pagination
     */
    public static final String CRUISE_REQUESTS_PAGINATION = "reqsPagination";
    public static final String CRUISE_DRIVERS_PAGINATION = "driversPagination";

    /**
     * Parameters for cruise adding
     */
    public static final String PARAMETER_CRUISE_DRIVER_ID = "driverId";
    public static final String PARAMETER_CRUISE_REQUEST_ID = "requestId";
    public static final String PARAMETER_CRUISE_STATUS = "status";

    /**
     * PAGINATION -> getting page
     * PAGE_COUNT -> page count
     */
    public static final String PAGINATION = "page";
    public static final int PAGE_COUNT = 10;

    /**
     * params of user
     */
    public static final String PARAMETER_PHONE_NUMBER = "phoneNumber";
    public static final String PARAMETER_PASSWORD = "password";


    /**
     * params of request
     */
    public static final String PARAMETER_REQUEST_NAME = "name";
    public static final String PARAMETER_REQUEST_FROM = "from";
    public static final String PARAMETER_REQUEST_TO = "to";
    public static final String INVALID_REQUEST_NAME = "request name can not be blank";
    public static final String INVALID_REQUEST_FROM = "request from name can not be blank";
    public static final String INVALID_REQUEST_TO = "request to name can not be blank";

    /**
     * params of car
     */
    public static final String PARAMETER_CAR_MODEL = "carModel";
    public static final String PARAMETER_CAR_NUMBER = "carNumber";
    public static final String INVALID_CAR_NUMBER = "invalid car number";
    public static final String INVALID_CAR_MODEL = "invalid car model";

    /**
     * params of driver
     */
    public static final String PARAMETER_DRIVER_FIRSTNAME = "firstName";
    public static final String PARAMETER_DRIVER_LASTNAME = "lastName";
    public static final String PARAMETER_DRIVER_PHONE_NUMBER = "phoneNumber";
    public static final String PARAMETER_DRIVER_PASSWORD = "password";
    public static final String PARAMETER_DRIVER_CAR_ID = "carId";
    public static final String INVALID_DRIVER_FIRSTNAME = "invalid driver first name";
    public static final String INVALID_DRIVER_LASTNAME = "invalid driver last name";
    public static final String INVALID_DRIVER_PHONE = "invalid driver phone number";
    public static final String INVALID_DRIVER_PASSWORD = "driver password invalid";
    public static final String INVALID_DRIVER_CAR_ID = "driver car id invalid";




    /**
     * carId -> car id parameter for get car id param ex. when delete command when editing command
     */
    public static final String PARAMETER_CAR_ID = "carId";

    /**
     * requestId -> car id parameter for get request id param ex. when delete command
     */
    public static final String PARAMETER_REQUEST_ID = "requestId";

    /**
     * PARAMETER_CURRENT_ID -> request param. We need when we know which entity is editing or deleting
     */
    public static final String PARAMETER_CURRENT_ID = "currentId";

    /**
     * REQ_ATTRIBUTE_FORM_INVALID => invalid form request param
     */
    public static final String REQ_ATTRIBUTE_FORM_INVALID = "invalid_form";
    public static final String REQ_ATTRIBUTE_USER_INVALID = "invalid_user";
    public static final String INVALID_USER_MESSAGE = "user not found";
    public static final String INVALID_PHONE_NUMBER_MESSAGE = "Phone number is not valid";
    public static final String INVALID_PASSWORD_MESSAGE = "Password is not valid";
    public static final String DELETING_MESSAGE = "invalidDeleting";
    public static final String EDITING_MESSAGE = "invalidEditing";
    public static final String REQ_ATTRIBUTE_USER_BLOCKED = "user_blocked";
    public static final String USER_BLOCKED_MESSAGE = "user is blocked";
    public static final String INVALID_CRUISE_REQUEST_ID = " Request id can not be blank";
    public static final String INVALID_CRUISE_DRIVER_ID = " Driver id can not be blank";


}
