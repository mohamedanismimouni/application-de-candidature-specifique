import { IColumn } from './column.model';
import { IRowContextMenuItem } from './row-context-menu-item.model';
import { IDatatableAction } from './datatable-action.model';


export interface IDatatableConfiguration {

    title: string;
    rowId: string;
    columns: Array<IColumn>;
    rowContextMenuItems: Array<IRowContextMenuItem>;
    datatableActions: Array<IDatatableAction>;
    pageSize: number;

}
