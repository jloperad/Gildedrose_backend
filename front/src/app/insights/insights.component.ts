import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { ItemService } from 'src/api/item.service';
import { Item, Types } from 'src/interfaces/item';

@Component({
  selector: 'insights',
  templateUrl: 'insights.component.html',
  styleUrls: ['insights.component.css'],
})
export class InsightsComponent implements OnInit, OnDestroy {
  itemsDictionary: { [key: string]: number } = {
    [Types.AGED]: 0,
    [Types.LEGENDARY]: 0,
    [Types.NORMAL]: 0,
    [Types.TICKETS]: 0,
  };

  unsubscribe$: Subject<void> = new Subject();

  get dictionaryKeys(): string[] {
    return Object.keys(this.itemsDictionary);
  }

  constructor(private router: Router, private itemsService: ItemService) {}

  getItems(): void {
    this.itemsService
      .getItems()
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe({
        next: (items: Item[]) => {
          items.forEach((item) => (this.itemsDictionary[item.type] += 1));
        },
      });
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  ngOnInit(): void {
    this.getItems();
  }

  onGoBack(): void {
    this.router.navigate(['..']);
  }
}
