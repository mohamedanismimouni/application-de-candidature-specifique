import { KeycloakService } from "keycloak-angular";
import { KEYCLOAK_HOST} from '../constants/api-urls.constant'

export function initializeKeycloak(
  keycloak: KeycloakService
  ) {
    return () =>
      keycloak.init({
        config: {
          url: KEYCLOAK_HOST + '/auth',
          realm: 'TALAN',
          clientId: 'web-siriushr',
        }  , 
        initOptions: {
          onLoad: 'check-sso',
          checkLoginIframe: false
      },
      enableBearerInterceptor: true,
      bearerPrefix: 'Bearer',
      bearerExcludedUrls: [
          '/assets'
       ]
      });
}