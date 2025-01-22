import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-pagination',
  imports: [CommonModule],
  templateUrl: './pagination.component.html',
  styleUrl: './pagination.component.css'
})
export class PaginationComponent {
  @Input() currentPage!: number;
  @Input() sizeOfPage!: number;
  @Input() totalItems!: number;
  @Output() pageChanged: EventEmitter<number> = new EventEmitter();

  get totalPages(): number {
    // console.log(this.totalItems + " and " + this.sizeOfPage + " division "+ Math.floor(this.totalItems / this.sizeOfPage));
    return Math.floor(this.totalItems / this.sizeOfPage);
  }

  changePage(page: number): void {
    if (page >= 0 && page <= this.totalPages) {
      this.currentPage = page;
      this.pageChanged.emit(page);
    }
  }

  get pageNumbers(): number[] {
    return Array.from(
      {
        length: this.totalPages
      },
      (_, index) => index + 1
    );
  }

  goToPage(event: Event): void {
    const page = event.target as HTMLInputElement;
    console.log("Test " + this.currentPage + " total " + this.totalPages);
    const pageNumber = parseInt(page.value, 10);
    if (pageNumber && pageNumber >= 0 && pageNumber <= this.totalPages && pageNumber !== this.currentPage) {
      this.currentPage = pageNumber;
      this.pageChanged.emit(pageNumber);
    }
  }
}
