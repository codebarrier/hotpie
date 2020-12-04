import { Data } from './data.model';

export class Checkpoint {
    checkpointHeader: string;
    dataExtractedFromCheckpoint: string[];
    checkpointReached: string;
    checkpointDescription: string;
    checkpointData: Data;
}