import { Component, effect } from '@angular/core';
import { Router } from '@angular/router';
import { initFlowbite } from 'flowbite';
import { AuthService } from '../../services/auth.service';
@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent {
  admin!: boolean;
  loginBtn: boolean = true;
  constructor(private authService: AuthService, private route: Router) {
    // let role = sessionStorage.getItem('role');
    // console.log(role);
    // console.log('role');
    // if(role =='ADMIN'){
    //   this.admin = true
    // }
    // effect(()=>{
    //   if(authService.loggedHaiKya() && sessionStorage.getItem('role')=='ADMIN'){
    //     this.admin = true
    //   }
    //   if(authService.loggedHaiKya()){
    //     this.loginBtn = false
    //   }
    // })
  }

  ngOnInit(): void {
    initFlowbite();
  }
  get isLoggedInAsAdmin() {
    return sessionStorage.getItem('_isLoggedIn') == 'true' && sessionStorage.getItem('role') == 'ADMIN' ? true : false;
  }
  get isLoggedIn() {
    return sessionStorage.getItem('_isLoggedIn') == 'true' ? true : false;
  }
  // ngDoCheck():void{
  //   let role = sessionStorage.getItem('role');
  //   console.log(role);
  //   if(role=='ADMIN'){
  //     this.admin = true
  //   }else{
  //     this.admin = false
  //   }
  //   // if(!this.authService.loginState()){
  //   //   this.loginBtn = true
  //   // }
  //   this.authService.loginStateTest.subscribe((res)=>{
  //     if(res== false){
  //       this.loginBtn = true
  //     }
  //   })
  //   this.authService.loginStateTest.subscribe((res)=>{
  //     if(res== false){
  //       this.loginBtn = true
  //     }
  // })
  //   if(role == 'ADMIN' ||  role == 'CUSTOMER'){
  //     this.loginBtn = false
  //   }
  // }
  navigateTo() {
    this.route.navigateByUrl('/profile');
  }
}
