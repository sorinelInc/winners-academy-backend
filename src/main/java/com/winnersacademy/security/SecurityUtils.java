//package org.vaadin.paul.spring.security;
//
//import com.vaadin.flow.server.ServletHelper.RequestType;
//import com.vaadin.flow.shared.ApplicationConstants;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.core.Authentication;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.stream.Stream;
//
///**
// * SecurityUtils takes care of all such static operations that have to do with
// * security and querying rights from different beans of the UI.
// */
//public final class SecurityUtils {
//
//    private SecurityUtils() {
//        // Util methods only
//    }
//
//
//    static boolean isFrameworkInternalRequest(HttpServletRequest request) {
//        final String parameterValue = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);
//        return parameterValue != null
//                && Stream.of(RequestType.values()).anyMatch(r -> r.getIdentifier().equals(parameterValue));
//    }
//
//    private static boolean isUserLoggedIn(Authentication authentication) {
//        return authentication != null
//                && !(authentication instanceof AnonymousAuthenticationToken);
//    }
//
//
//}
