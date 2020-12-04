export class Data {
    dataId: string;
    data: string;
    dataSeverity: string;
    pinned: boolean = false;

    public getDataSeverityColour(): string {
        if (this.dataSeverity === 'HIGH') {
            return 'red';
        } else if (this.dataSeverity === 'MEDIUM') {
            return 'orange';
        } else if (this.dataSeverity === 'LOW') {
            return 'yellow'
        } else {
            return 'green';
        }
    }
}