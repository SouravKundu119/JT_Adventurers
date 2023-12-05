import { Component } from '@angular/core';
import { GreetingService } from 'src/app/core/services/greeting.service';

@Component({
  selector: 'app-admin-stats',
  templateUrl: './admin-stats.component.html',
  styleUrls: ['./admin-stats.component.css'],
})
export class AdminStatsComponent {
  quote!: any;
  greeting!: string;
  cardList = [
    {
      title: 'Revenue',
      statNumber: 10000,
      icon: 'assets/gifs/revenue.gif',
    },
    {
      title: 'Tours',
      statNumber: 6,
      icon: 'assets/gifs/tours.gif',
    },
    {
      title: 'Batches',
      statNumber: 52,
      icon: 'assets/gifs/batches.gif',
    },
    {
      title: 'Enrolled',
      statNumber: 15000,
      icon: 'assets/gifs/enrolled.gif',
    },
  ];
  constructor(private greetingService: GreetingService) {}
  ngOnInit() {
    const date = new Date();
    let hours = date.getHours();
    this.greeting =
      hours < 12
        ? 'Morning'
        : hours <= 18 && hours >= 12
        ? 'Afternoon'
        : 'Night';
    this.greetingService.getRandomQuote().subscribe((res) => {
      this.quote = res;
      console.log(this.quote);
    });
  }
}
