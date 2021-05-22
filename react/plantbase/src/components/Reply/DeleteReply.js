import { useState } from 'react';
import Modal from 'react-bootstrap/Modal';
import { useHistory } from 'react-router-dom';
import { deleteReplyById } from '../../services/reply-api';

function DeleteReply( {replyId, deleteReplyByReplyId} ) {
    const [show, setShow] = useState(false);
    const history = useHistory();

    const showModal = () => {
        setShow(true);
    };
    
    const hideModal = () => {
        setShow(false);
    };

    const handleDelete = () => {
        deleteReplyById(replyId);
        deleteReplyByReplyId(replyId);
        hideModal();
    }

    return(
        <>
        <button onClick={showModal} className="btn btn-success">Delete</button>
        <Modal show={show} onHide={hideModal}>
            <Modal.Header>
            <Modal.Title>Stop!</Modal.Title>
            </Modal.Header>
            <Modal.Body>Are you sure you want to delete this reply?</Modal.Body>
            <Modal.Footer>
                <button onClick={hideModal}>No</button>
                <button onClick={handleDelete}>Yes</button>
            </Modal.Footer>
        </Modal>
        </>
    );
}

export default DeleteReply;