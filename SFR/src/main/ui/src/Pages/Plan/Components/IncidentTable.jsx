import React, { Component } from 'react';
import { Table, Button, Accordion, OverlayTrigger, Tooltip } from "react-bootstrap";
import AddIncidentModal from './AddIncidentModal';
import GenericModal from '../../../SharedComponents/GenericModal/GenericModal';

class IncidentTable extends Component {
    constructor(props) {
        super(props);
        this.state = {
            riesgos: [],
            incidentes: [],
            showIncidentModal: false,
            showDel: false,
            delID: ""
        };
        this.removeIncident = this.removeIncident.bind(this);
        this.openModalAddIncident = this.openModalAddIncident.bind(this);
        this.closeModalAddIncident = this.closeModalAddIncident.bind(this);
        this.openModalDelIncident = this.openModalDelIncident.bind(this);
        this.closeModalDelIncident = this.closeModalDelIncident.bind(this);
    }

    removeIncident() {
        this.props.removeRisks(this.state.delID);
        this.closeModalDelRisk();
    }

    openModalAddIncident() {
        this.setState({ showIncidentModal: true });
    };

    closeModalAddIncident() {
        this.setState({ showIncidentModal: false });
    };

    openModalDelIncident(id) {
        this.setState({ showDel: true, delID: id });
    };

    closeModalDelIncident() {
        this.setState({ showDel: false, delID: "" });
    };

    render() {
        return (
            <div>
                {/* Mobile */}
                <div className='d-lg-none container-fluid'>
                
                </div>
                {/* PC */}
                <div className="d-none d-lg-block">
                    <h1>xd</h1>
                </div>
                <AddIncidentModal
                    risks={this.props.riesgos}
                    addIncident={this.props.addIncident}
                    show={this.state.showIncidentModal}
                    closeModal={this.closeModalAddIncident} />
                <GenericModal
                    show={this.state.showDel}
                    close={this.closeModalDelIncident}
                    action={this.removeIncident}
                    header={"Eliminar incidente de un Plan"}
                    body={"¿Esta seguro que desea eliminar este incidente del Plan?"} />
            </div>
        );
    }
};
export default IncidentTable;