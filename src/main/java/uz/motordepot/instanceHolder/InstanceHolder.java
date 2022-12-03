package uz.motordepot.instanceHolder;


import uz.motordepot.dao.contract.*;
import uz.motordepot.dao.impls.*;
import uz.motordepot.mappers.*;
import uz.motordepot.service.contract.*;
import uz.motordepot.service.impls.*;
import uz.motordepot.utils.encoder.PasswordEncoder;
import uz.motordepot.utils.validator.PatternValidator;

public class InstanceHolder {
    private static final PasswordEncoder passwordEncoder = new PasswordEncoder();
    private static final RoleDao roleDao = new RoleDaoImpl();
    private static final UserDao userDao = new UserDaoImpl();
    private static final CarDao carDao = new CarDaoImpl();
    private static final RequestDaoImpl requestDao = new RequestDaoImpl();
    private static final DriverDao driverDao = new DriverDaoImpl();
    private static final CarMapper carMapper = new CarMapper();
    private static final UserMapper userMapper = new UserMapper();
    private static final DriverMapper driverMapper = new DriverMapper();
    private static final RequestMapper requestMapper = new RequestMapper();
    private static final CruiseMapper cruiseMapper = new CruiseMapper();
    private static final CarService carService = new CarServiceImpl();
    private static final UserService userService = new UserServiceImpl();
    private static final RequestService requestService = new RequestServiceImpl();
    private static final DriverService driverService = new DriverServiceImpl();
    private static final CruiseDao cruiseDao = new CruiseDaoImpl();
    private static final CruiseService cruiseService = new CruiseServiceImpl();
    private static final PatternValidator patternValidator = new PatternValidator();

    public static <T> T getInstance(Class<?> clazz) {
        return switch (clazz.getSimpleName()) {
            case "PatternValidator" -> (T) patternValidator;
            case "PasswordEncoder" -> (T) passwordEncoder;
            case "RoleDao" -> (T) roleDao;
            case "UserDao" -> (T) userDao;
            case "CarDao" -> (T) carDao;
            case "RequestDao" -> (T) requestDao;
            case "DriverDao" -> (T) driverDao;
            case "CruiseDao" -> (T) cruiseDao;
            case "CarMapper" -> (T) carMapper;
            case "UserMapper" -> (T) userMapper;
            case "CruiseMapper" -> (T) cruiseMapper;
            case "DriverMapper" -> (T) driverMapper;
            case "RequestMapper" -> (T) requestMapper;
            case "CarService" -> (T) carService;
            case "UserService" -> (T) userService;
            case "RequestService" -> (T) requestService;
            case "CruiseService" -> (T) cruiseService;
            case "DriverService" -> (T) driverService;
            default -> null;
        };
    }
}