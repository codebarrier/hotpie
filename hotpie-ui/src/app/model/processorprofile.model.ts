import { LineDemarcationProcessor } from './linedemarcation.model';
import { FilterProcessor } from './filter.model';
import { HighlightConfig } from './highlightconfig.model';
import { SeverityConfig } from './severityconfig.model';
import { CheckpointConfiguration } from './checkpointconfig.model';
import { GroupConfig } from './groupconfig.model';

export class ProcessorPipelineProfile {
    public profileName: string;
    public profileDescription: string;
    public lineDemarcation: LineDemarcationProcessor;
    public filters: FilterProcessor[];
    public highlights: HighlightConfig[];
    public severities: SeverityConfig[];
    public checkpoints: CheckpointConfiguration[];
    public groups: GroupConfig[];
}