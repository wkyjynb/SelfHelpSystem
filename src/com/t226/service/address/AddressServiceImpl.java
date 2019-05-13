package com.t226.service.address;

import com.t226.dao.address.AddressMapper;
import com.t226.pojo.Address;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class AddressServiceImpl implements  AddressService{
    @Resource
    public AddressMapper addressMapper;
    @Override
    public List<Address> getAddressList() {
        return addressMapper.getAddressList();
    }
}
