import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Item, Types } from 'src/interfaces/item';

@Component({
  selector: 'item-form-dialog',
  templateUrl: 'item-form-dialog.component.html',
  styleUrls: ['item-form-dialog.component.css'],
})
export class ItemFormDialogComponent implements OnInit {
  isUpdating = false;
  types = Object.values(Types);

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<ItemFormDialogComponent>,
    @Inject(MAT_DIALOG_DATA) private data: { item: Item }
  ) {}

  form = this.fb.group({
    name: ['', [Validators.required]],
    sellIn: ['', [Validators.required]],
    quality: ['', [Validators.required, Validators.min(0), Validators.max(80)]],
    type: ['', [Validators.required]],
  });

  ngOnInit(): void {
    this.isUpdating = !!this.data;
    if (this.isUpdating) {
      const { id, ...content } = this.data.item;
      this.form.setValue({ ...content });
    }
  }

  onConfirm(): void {
    this.dialogRef.close({ ...this.form.value });
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}
