import { Component, Input, Output, EventEmitter } from '@angular/core';

import { Subject } from 'rxjs';

import * as shape from 'd3-shape';

import { INode } from './models/node.model';
import { ILink } from './models/link.model';
import { IGraphConfiguration } from './models/graph-configuration.model';

import { STARTING_POINT } from './constants/graph.constant';


@Component({
    selector: 'app-graph',
    templateUrl: './graph.component.html',
    styleUrls: [ './graph.component.scss' ]
})
export class GraphComponent {

    nodesValue: Array<INode> = [];

    @Input()
    get nodes() {
        return this.nodesValue;
    }

    set nodes(nodes: Array<INode>) {
        if (this.configuration?.withStartingPoint) {
            this.nodesValue = [ ...nodes, {
                id: this.startingPoint.id,
                label: this.startingPoint.label,
                data: this.startingPoint,
                meta: {
                    selectable: this.configuration?.selectableStartingPoint,
                    covered: true,
                    reachable: true
                }
            } ];
        } else {
            this.nodesValue = nodes ? [ ...nodes ] : [];
        }
    }

    linksValue: Array<ILink> = [];

    @Input()
    get links() {
        return this.linksValue;
    }

    set links(links: Array<ILink>) {
        if (this.configuration?.withStartingPoint) {
            const linksValue: Array<ILink> = [ ...links ];
            this.nodes.forEach((node) => {
                let initial = true;
                for (const link of links) {
                    if (link.target === node.id || node.id === this.startingPoint.id) {
                        initial = false;
                        break;
                    }
                }
                if (initial) {
                    linksValue.push({
                        id: 'link-' + linksValue.length.toString(),
                        source: this.startingPoint.id,
                        target: node.id,
                        data: undefined
                    });
                }
            });
            this.linksValue = [ ...linksValue ];
        } else {
            this.linksValue = links ? [ ...links ] : [];
        }
    }

    selectedNodeValue: INode;

    @Input()
    get selectedNode() {
        return this.selectedNodeValue;
    }

    set selectedNode(selectedNode: INode) {
        this.selectedNodeValue = selectedNode;
        if (this.selectedNode) {
            this.panToNode.next(this.selectedNode.id);
        } else {
            this.panToNode.next('');
            this.center.next(true);
        }
        this.selectedNodeChange.emit(this.selectedNodeValue);
    }

    @Output()
    selectedNodeChange = new EventEmitter<INode>();

    @Input()
    configuration: IGraphConfiguration;

    startingPoint = STARTING_POINT;
    curve = shape.curveBundle.beta(1);
    panToNode: Subject<string> = new Subject();
    center: Subject<boolean> = new Subject();

    onNodeSelection(node: INode) {
        if (!node.meta.selectable) {
            return;
        }
        if (this.selectedNode && node.id === this.selectedNode.id) {
            this.selectedNode = undefined;
        } else {
            this.selectedNode = node;
        }
    }

}
