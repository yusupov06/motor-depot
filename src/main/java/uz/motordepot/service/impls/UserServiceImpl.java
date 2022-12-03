package uz.motordepot.service.impls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uz.motordepot.dao.contract.UserDao;
import uz.motordepot.entity.User;
import uz.motordepot.instanceHolder.InstanceHolder;
import uz.motordepot.mappers.UserMapper;
import uz.motordepot.pagination.Page;
import uz.motordepot.pagination.PageRequest;
import uz.motordepot.payload.UserAddDTO;
import uz.motordepot.payload.UserDTO;
import uz.motordepot.service.contract.UserService;
import uz.motordepot.utils.Utils;
import uz.motordepot.utils.encoder.PasswordEncoder;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger();
    private final UserMapper mapper = InstanceHolder.getInstance(UserMapper.class);
    private final UserDao dao = InstanceHolder.getInstance(UserDao.class);
    private final PasswordEncoder passwordEncoder = InstanceHolder.getInstance(PasswordEncoder.class);

    @Override
    public Optional<UserDTO> authenticate(String email, String password) {
        logger.info("User authenticate method called");
        try {
            if (email == null) {
                logger.error("Passed null as email");
                return Optional.empty();
            }
            if (password == null) {
                logger.error("Passed null as password");
                return Optional.empty();
            }
            password = passwordEncoder.encode(password);

            User user = dao
                    .findByPhoneNumberAndPassword(email, password).orElse(null);
            return Optional.ofNullable(mapper.toDto(user));

        } catch (NoSuchAlgorithmException e) {
            logger.error("NoSuchAlgorithmException occured");
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean add(UserAddDTO userAddDto) {
        return false;
    }

    @Override
    public boolean edit(Long id, UserAddDTO userAddDto) {
        return false;
    }

    @Override
    public void delete(Long id) {
    }

    @Override
    public List<UserDTO> findAll() {
        logger.info("Finding all users method called");
        return dao.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public Page<UserDTO> findByPage(int page, int size) {
        logger.info("FINDING USERS BY PAGINATION METHOD CALLED");
        PageRequest pageRequest = new PageRequest(page, 10);
        int totalPages = dao.findTotalPages(pageRequest.getSize(), UserDao.TOTAL_COUNT_QUERY);
        List<UserDTO> allByPage = dao
                .findByPage(pageRequest.getPage(), pageRequest.getSize())
                .stream()
                .map(mapper::toDto)
                .toList();

        return Utils.getPage(pageRequest.getPage(), totalPages, allByPage);
    }
}
