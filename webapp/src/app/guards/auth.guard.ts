import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { KeycloakAuthGuard, KeycloakService } from 'keycloak-angular';
import { AuthenticationService } from '../services/authentication.service';
import { StorageService } from '../services/storage.service';
import { UserService } from '../services/user.service';
import { IRedirectUrl } from '../models/redirect-url.model';
import { StorageKeyEnum } from '../enumerations/storage-key.enum';
import { ICollaborator } from '../models/collaborator.model';
import { ProfileTypeEnum } from '../enumerations/profile-type.enum';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard extends KeycloakAuthGuard {

  constructor(
    private userService: UserService,
    private authenticationService: AuthenticationService,
    protected readonly router: Router,
    private keycloak: KeycloakService,
    private storageService: StorageService,


  ) {
    super(router, keycloak);
  }

  async isAccessAllowed(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Promise<boolean | UrlTree> {

    if (!this.authenticated) {
      await this.keycloak.login({
        redirectUri: window.location.origin + state.url,
      });
    }else {


            let profile=await this.keycloakAngular.loadUserProfile()
            let currentUser= await this.getUserByEmail(profile.email)
      
            
            //Check roles
            const requiredRoles = route.data.roles;
            let granted: boolean = false;
            if (!requiredRoles || requiredRoles.length === 0) {
              granted = true;
            } else {
              for (const requiredRole of requiredRoles) {
                if (this.roles.indexOf(requiredRole) > -1) {
                  granted = true;
                  break;
                }
              }
            }
      
            if(granted === false) {
              this.router.navigate(['/']);
            }
            return granted;
      
        
  

    }


  }

  getUserByEmail(email): Promise<any>{
    return new Promise((resolve, reject) => {
        this.userService.getUserByEmail(email).subscribe(
            (currentUser: ICollaborator) => {
                const redirectUrl: IRedirectUrl = this.storageService.getItem(StorageKeyEnum.REDIRECT_URL_KEY);
                var roles = [];
                roles =this.keycloakAngular.getKeycloakInstance().resourceAccess["web-siriushr"].roles;
                //user role
                if((roles.length>1))
                {
                  if(roles.includes(ProfileTypeEnum.MANAGER))
                  {
                    currentUser.profileType=ProfileTypeEnum.MANAGER
                    if (roles.includes(ProfileTypeEnum.HR_RESPONSIBLE))
                    {
                      currentUser.profileType=ProfileTypeEnum.HR_RESPONSIBLE
                    }
                  }

                  if (roles.includes(ProfileTypeEnum.HR_RESPONSIBLE)){
                    currentUser.profileType=ProfileTypeEnum.HR_RESPONSIBLE
                  }

                  if (roles.includes(ProfileTypeEnum.SUPPORT)){
                    currentUser.profileType=ProfileTypeEnum.SUPPORT
                  }

                }else if ((roles.length===1)&&roles.includes(ProfileTypeEnum.COLLABORATOR))
                {
                  currentUser.profileType=ProfileTypeEnum.COLLABORATOR
                }else if ((roles.length===1) && (roles.includes(ProfileTypeEnum.MANAGER)))
                {
                  currentUser.profileType=ProfileTypeEnum.MANAGER
                }
                else if ((roles.length===1)&& (roles.includes(ProfileTypeEnum.HR_RESPONSIBLE)))
                {
                  currentUser.profileType=ProfileTypeEnum.HR_RESPONSIBLE
                }
                else if ((roles.length===1)&& (roles.includes(ProfileTypeEnum.SUPPORT)))
                {
                  currentUser.profileType=ProfileTypeEnum.SUPPORT
                }

                 //save current user

                 this.storageService.setItem(StorageKeyEnum.CURRENT_USER_KEY, currentUser);
                 //change status
                 this.authenticationService.setAuthenticationStatus(true);

                resolve(currentUser)
                })

    })
  }

}
