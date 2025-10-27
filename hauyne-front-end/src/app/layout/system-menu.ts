export interface SystemMenu {
    id: number,
    name: string,
    title: string,
    path: string,
    level: number,
    order: number,
    disabled: boolean,
    icon: string,
    selected: boolean,
    children: SystemMenu[]
}
