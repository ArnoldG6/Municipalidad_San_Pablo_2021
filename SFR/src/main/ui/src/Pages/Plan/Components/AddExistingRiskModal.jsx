import React, { Component } from 'react';
import '../Plan.css';
import { Modal, Button, Table, InputGroup } from "react-bootstrap";
import 'react-toastify/dist/ReactToastify.css';

class AddExistingRiskModal extends Component {
    constructor(props) {
        super(props);
        this.state = {
            riskIDs: []
        }
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleSelect = this.handleSelect.bind(this);
    }

    handleSubmit() {
        this.setState({ riskIDs: [] });
        this.props.closeModal();
        this.props.addRisk(this.state.riskIDs);
    }

    handleSelect(id) {
        let list = this.state.riskIDs;
        if (list.includes(id)) {
            list = list.filter(function (value) { return value !== id });
            this.setState({ riskIDs: list });
        } else {
            list.push(id);
            this.setState({ riskIDs: list });
        }
    }

    render() {
        let show = this.props.show;
        let closeModal = this.props.closeModal;
        return (
            <Modal show={show} onHide={closeModal} >
                <Modal.Header closeButton>
                    Agregar Riesgos al Plan
                </Modal.Header>
                <Modal.Body>
                    {(typeof this.props.risks === 'undefined' || this.props.risks === null) ? <h1>No se han agregado riesgos</h1> :
                        this.props.risks.length === 0 ? <h1>No hay riesgos disponibles</h1> :
                            <Table>
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Nombre</th>
                                        <th>Tipo General</th>
                                        <th>Tipo por Área</th>
                                        <th>Tipo Específico</th>
                                        <th></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {this.props.risks.map((risk) => {
                                        return (
                                            <tr key={risk.id}>
                                                <td>{risk.id}</td>
                                                <td className="nameSlot">{risk.name}</td>
                                                <td>{risk.generalType}</td>
                                                <td>{risk.areaType}</td>
                                                <td>{risk.specType}</td>
                                                <td>
                                                    <InputGroup.Checkbox
                                                        aria-label="Seleccionar Riesgo"
                                                        name="selectRisk"
                                                        value={risk.id}
                                                        onClick={() => { this.handleSelect(risk.id) }} />
                                                </td>
                                            </tr>
                                        )
                                    })}
                                </tbody>
                            </Table>
                    }
                    <Button className='btn-sfr' id="submit-button-new-item" onClick={this.handleSubmit}
                        disabled={(typeof this.props.risks === 'undefined' || this.props.risks === null) ? true :
                            this.props.risks.length === 0 ? true : false}>
                        Guardar
                    </Button>
                </Modal.Body>
            </Modal >
        );
    };
};
export default AddExistingRiskModal;