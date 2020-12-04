import { PipeTransform, Pipe } from '@angular/core';

@Pipe({ name: 'highlight' })
export class HighlightPipe implements PipeTransform {
    transform(text: string, search): string {
        if (search === null || search === '' || search === undefined) {
            return text;
        }
        var pattern = search.replace(/[\-\[\]\/\{\}\(\)\*\+\?\.\\\^\$\|]/g, "\\$&");
        pattern = pattern.split(' ').filter((t) => {
            return t.length > 0;
        }).join('|');
        var regex = new RegExp(pattern, 'gi');

        return search ? text.replace(regex, (match) => `<mark>${match}</mark>`) : text;
    }
}