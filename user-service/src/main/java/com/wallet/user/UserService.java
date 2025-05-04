package com.wallet.user;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final String USER_CREATE = "user_create";

    @Autowired
    UserRepository userRepository;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public User create(User user) {

        user = userRepository.save(user);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", user.getId());
        jsonObject.put("userEmail", user.getEmail());
        jsonObject.put("userContact", user.getContact());

        kafkaTemplate.send(USER_CREATE, jsonObject.toJSONString());

        return user;
    }

    public User getUserById(int id) {
          return userRepository.findById(id).orElse(null);
    }
}
