import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { ItemService } from 'src/api/item.service';
import { Item } from 'src/interfaces/item';

@Component({
  selector: 'item',
  templateUrl: 'item.component.html',
  styleUrls: ['item.component.css'],
})
export class ItemComponent implements OnInit, OnDestroy {
  item: any;
  unsubscribe$: Subject<void> = new Subject();

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private itemsService: ItemService
  ) {}

  getItem(): void {
    const id = this.route.snapshot.params['id'];
    this.itemsService
      .getItem(id)
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe({
        next: (item: Item) => (this.item = item),
      });
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  ngOnInit(): void {
    this.getItem();
  }

  onGoBack(): void {
    this.router.navigate(['..']);
  }
}
