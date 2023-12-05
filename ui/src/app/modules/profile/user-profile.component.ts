import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { AuthService } from 'src/app/core/services/auth.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent {
  image: string = 'assets/illustrations/Connected world-amico 3.png'
  constructor(private router: Router,private authService:AuthService) {}


  ngOnInit() {
    console.log(`Profile page for: ${this.authService.getRole()}`)
  }

  logout() {
    sessionStorage.clear()
    this.authService.isLoggedIn.next(false)
    this.router.navigate([''])
    return;
    this.router.navigate(['/']);
  }
}
