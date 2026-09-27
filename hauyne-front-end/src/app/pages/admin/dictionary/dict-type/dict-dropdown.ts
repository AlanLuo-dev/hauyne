export interface DictDropdown {
    dictTypeCode: string;
    options: Option[];
}

export interface Option {
    value: string;
    label: string;
    icon?: string;
    iconType?: string;
}
