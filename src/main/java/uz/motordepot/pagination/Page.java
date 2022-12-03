package uz.motordepot.pagination;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
public class Page<T> {

    private List<T> items = new ArrayList<>();
    private int totalPages;
    private int currentPage;
    private int nextPage;
    private int prevPage;
    private boolean hasNext;
    private boolean hasPrev;
    private int size;

    public void setItems(List<T> items) {
        this.items = items;
        size = items.size();
    }

    public Page(List<T> items,
                int totalPages,
                int currentPage,
                int nextPage,
                int prevPage,
                boolean hasNext,
                boolean hasPrev) {
        this.items = items;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.nextPage = nextPage;
        this.prevPage = prevPage;
        this.hasNext = hasNext;
        this.hasPrev = hasPrev;
        this.size = items.size();
    }

    public int getSize(){
        return items.size();
    }

}
