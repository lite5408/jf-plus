package cn.iutils.common.filter;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

/**
 * 
 * 注意，当需要分别定义处理普通用户和管理员验证的Realm时，对应Realm的全类名应该包含字符串“User”，或者“Admin”。
 */
public class CustomModularRealmAuthenticator extends ModularRealmAuthenticator {
	@Override
	protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		// 判断getRealms()是否返回为空
		assertRealmsConfigured();
		// 强制转换回自定义的CustomizedToken
		CustomUserPasswordToken customizedToken = (CustomUserPasswordToken) authenticationToken;
		// 登录类型
		String loginType = customizedToken.getLoginType();
		// 所有Realm
		Collection<Realm> realms = getRealms();
		// 登录类型对应的所有Realm
		Collection<Realm> typeRealms = new ArrayList<>();
		for (Realm realm : realms) {
			if (realm.getName().contains(loginType))
				typeRealms.add(realm);
		}

		// 判断是单Realm还是多Realm
		if (typeRealms.size() == 1)
			return doSingleRealmAuthentication(typeRealms.iterator().next(), customizedToken);
		else
			return doMultiRealmAuthentication(typeRealms, customizedToken);
	}

}
