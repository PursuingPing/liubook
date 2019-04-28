import com.classbooking.web.dao.UserDao;
import com.classbooking.web.domain.User;
import com.classbooking.web.util.CodeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void testInsert(){
        String email="11111@qq.com";
        String code="fdsafsd";
        String password="123456";
        Integer state=1;
        User user = new User();
        user.setPassword(password);
        user.setEmail(email);
        user.setCode(code);
        user.setState(state);
        System.out.println(userDao.register(user));
    }

    @Test
    public void testFindByCode(){
        String code="fdsafsd";
        User user= userDao.findByCode(code);
        System.out.println(user.getCode()+user.getEmail()+user.getPassword()+user.getState()+user.getId());
    }

    @Test
    public void testUpdateUser(){
        User user = new User();
        user.setId(2);
        user.setCode("hhhh");
        user.setEmail("12333@qq.com");
        user.setState(2);
        user.setPassword("213");

        System.out.println(userDao.updateUser(user));

    }

    @Test
    public void testUUIDUtil(){
        for(int i=0; i<3; i++){
            System.out.println(CodeUtil.generateUniqueCode());
        }
    }

    @Test
    public void testFindByEmail(){
        System.out.println(userDao.findByEmail("1140131901@qq.com"));
    }
}
