import { Data } from './data.model';
import { Highlight } from './highlight.model';
import { Checkpoint } from './checkpoint.model';

export class Group {
    groupName: string;
    groupId: String;
    groupTitle: string;
    highlights: Highlight[];
    groupData: Data[];
    subGroups: Group[];
    active: boolean = false;
    expanded: boolean = false;
    highestSeverity: string;
    checkpoints: Checkpoint[];
}