package com.reckue.post.util.converter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reckue.post.exception.ReckueIllegalArgumentException;
import com.reckue.post.generated.controller.dto.NodeRequestDto;
import com.reckue.post.generated.controller.dto.NodeResponseDto;
import com.reckue.post.generated.controller.dto.NodeTypeDto;
import com.reckue.post.generated.controller.dto.StatusTypeDto;
import com.reckue.post.model.Node;
import com.reckue.post.model.type.NodeType;
import com.reckue.post.model.type.StatusType;
import com.reckue.post.transfer.node.NodeParentResponse;
import com.reckue.post.transfer.node.audio.AudioNodeRequest;
import com.reckue.post.transfer.node.audio.AudioNodeResponse;
import com.reckue.post.transfer.node.code.CodeNodeRequest;
import com.reckue.post.transfer.node.code.CodeNodeResponse;
import com.reckue.post.transfer.node.image.ImageNodeRequest;
import com.reckue.post.transfer.node.image.ImageNodeResponse;
import com.reckue.post.transfer.node.list.ListNodeRequest;
import com.reckue.post.transfer.node.list.ListNodeResponse;
import com.reckue.post.transfer.node.poll.PollNodeRequest;
import com.reckue.post.transfer.node.poll.PollNodeResponse;
import com.reckue.post.transfer.node.text.TextNodeRequest;
import com.reckue.post.transfer.node.text.TextNodeResponse;
import com.reckue.post.transfer.node.video.VideoNodeRequest;
import com.reckue.post.transfer.node.video.VideoNodeResponse;
import org.modelmapper.ModelMapper;

import java.time.ZoneId;
import java.util.Map;

/**
 * Class for converting NodeRequest object to Node and Node object to NodeResponse.
 *
 * @author Viktor Grigoriev
 * @author Dmitrii Lebedev
 */
public class NodeConverter {

    private static final ModelMapper mapper = new ModelMapper();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Converts from NodeRequest to Node.
     *
     * @param nodeRequest the object of class NodeRequest
     * @return the object of class Node
     */
    public static Node convertToModel(NodeRequestDto nodeRequest) {
        if (nodeRequest == null) {
            throw new ReckueIllegalArgumentException("Null parameters are not allowed");
        }

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Map<NodeTypeDto, Class<?>> nodeRequestTypes = Map.of(
                NodeTypeDto.TEXT, TextNodeRequest.class,
                NodeTypeDto.IMAGE, ImageNodeRequest.class,
                NodeTypeDto.VIDEO, VideoNodeRequest.class,
                NodeTypeDto.CODE, CodeNodeRequest.class,
                NodeTypeDto.LIST, ListNodeRequest.class,
                NodeTypeDto.AUDIO, AudioNodeRequest.class,
                NodeTypeDto.POLL, PollNodeRequest.class
        );

        Class<?> nodeClass = nodeRequestTypes.get(nodeRequest.getType());

        return Node.builder()
                .id(nodeRequest.getId())
                .type(Converter.convert(nodeRequest.getType(), NodeType.class))
                .content(objectMapper.convertValue(nodeRequest.getContent(), nodeClass))
                .status(Converter.convert(nodeRequest.getStatus(), StatusType.class))
                .build();
    }

    /**
     * Converts from Node to NodeResponse.
     *
     * @param node the object of class Node
     * @return the object of class NodeResponse
     */
    public static NodeResponseDto convertToDto(Node node) {
        if (node == null) {
            throw new ReckueIllegalArgumentException("Null parameters are not allowed");
        }

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Map<NodeType, Class<?>> nodeTypeClassMap = Map.of(
                NodeType.TEXT, TextNodeResponse.class,
                NodeType.IMAGE, ImageNodeResponse.class,
                NodeType.VIDEO, VideoNodeResponse.class,
                NodeType.CODE, CodeNodeResponse.class,
                NodeType.LIST, ListNodeResponse.class,
                NodeType.AUDIO, AudioNodeResponse.class,
                NodeType.POLL, PollNodeResponse.class
        );

        Class<?> nodeClass = nodeTypeClassMap.get(node.getType());

        return NodeResponseDto.builder()
                .id(node.getId())
                .type(mapper.map(node.getType(), NodeTypeDto.class))
                .userId(node.getUserId())
                .createdDate(node.getCreatedDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .modificationDate(node.getModificationDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .content(objectMapper.convertValue(node.getContent(), nodeClass))
                .status(Converter.convert(node.getStatus(), StatusTypeDto.class))
                .build();
    }
}
