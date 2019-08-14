package edu.gzhu.its.system.service.impl;

import org.springframework.stereotype.Service;

import edu.gzhu.its.base.dao.impl.BaseDAOImpl;
import edu.gzhu.its.system.entity.Role;
import edu.gzhu.its.system.service.IRoleService;

@Service("roleService")
public class RoleService extends BaseDAOImpl<Role, Long> implements IRoleService{

}
