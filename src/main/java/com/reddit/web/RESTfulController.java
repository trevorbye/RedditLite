package com.reddit.web;

import com.reddit.POJO.AjaxResponse;
import com.reddit.POJO.CommentResponse;
import com.reddit.POJO.NewPostResponse;
import com.reddit.model.*;
import com.reddit.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.sql.Date;
import java.util.*;

import static com.reddit.POJO.NodeTraverse.firstLevelNodes;
import static com.reddit.POJO.NodeTraverse.traverseNodes;


@RestController
public class RESTfulController {

    @Autowired
    private PostService postService;

    @Autowired
    private NodeService nodeService;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private LikeTrackerService likeTrackerService;


    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

    //default home page mapping returns first page of posts
    @RequestMapping("/homelist")
    public Page<PostEntity> mainPage() {
        return postService.getPage(0);
    }

    @RequestMapping("/pageList")
    public Page<PostEntity> selectPage(@RequestBody int pageNum) {
        return postService.getPage(pageNum);
    }

    @RequestMapping("/page-count")
    public List<Integer> numberOfPages() {
        double postCount = postService.countOfPosts();
        long numOfPages = (long) Math.ceil(postCount / 20);
        List<Integer> pageList = new ArrayList<>();

        for (int i = 1; i <= numOfPages; i++) {
            pageList.add(i);
        }

        return pageList;
    }

    @RequestMapping("/post/{postId}")
    public PostEntity postPage(@PathVariable int postId) {
        //list of all comments for this post based on postId
        List<NodeEntity> commentList = nodeService.commentsByPost(postId);
        //get root post
        PostEntity post = postService.findPostById(postId);
        //populate list of nodes in post object with first-level comments
        post.setNodeList(firstLevelNodes(commentList,postId));

        //initialize recursive method by starting loop through first-level comments
        for (NodeEntity node : post.getNodeList()) {
            traverseNodes(commentList,node);
        }

        //return post with nested lists populated
        return post;
    }



    @RequestMapping("/like/{postId}")
    public int likeReturn(@PathVariable int postId, Principal principal) {
        int code;

        //check if user has already liked this post
        LikeTrackerEntity combination = likeTrackerService.findPostLike(principal.getName(), postId);

        //increment like-count if combo does not exist, else send error response
        if (combination == null) {
            PostEntity post = postService.findPostById(postId);
            post.setLikes(post.getLikes() + 1);
            postService.createPost(post);

            //add like combination to db to prevent duplicate likes
            LikeTrackerEntity newCombination = new LikeTrackerEntity();
            newCombination.setPostId(postId);
            newCombination.setNodeId(0);
            newCombination.setUsername(principal.getName());

            likeTrackerService.persistLikeCombination(newCombination);

            code = 200;
            return code;
        } else {
            code = 404;
            return code;
        }
    }

    @RequestMapping("/like/node/{nodeId}")
    public int nodeLikeReturn(@PathVariable int nodeId, Principal principal) {
        int code;

        LikeTrackerEntity combination = likeTrackerService.findNodeLike(principal.getName(),nodeId);

        if (combination == null) {
            NodeEntity node = nodeService.findByNodeId(nodeId);
            node.setLikes(node.getLikes() + 1);
            nodeService.save(node);

            //add like combination
            LikeTrackerEntity newCombination = new LikeTrackerEntity();
            newCombination.setUsername(principal.getName());
            newCombination.setPostId(0);
            newCombination.setNodeId(nodeId);
            likeTrackerService.persistLikeCombination(newCombination);

            code = 200;
            return code;
        } else {
            code = 404;
            return code;
        }
    }

    @RequestMapping("/add-comment/{switchId}")
    public NodeEntity addingComment(@RequestBody CommentResponse commentResponse, Principal principal, @PathVariable int switchId) {
        Date now = new Date(Calendar.getInstance().getTimeInMillis());

        //add comment to db
        NodeEntity node = new NodeEntity();
        node.setParentId(commentResponse.getParentId());
        node.setPostId(commentResponse.getRootPostId());
        node.setBody(commentResponse.getComment());

        node.setUsername(principal.getName());
        node.setLikes(0);
        node.setCmmnts(0);
        node.setSubmit_date(now);

        //increment comment-count on parent node
        //if comment is on the root post, increment post comment count, else increment node comment count
        if (switchId == 2) {
            NodeEntity parentNode;
            parentNode = nodeService.findByNodeId(commentResponse.getParentId());
            parentNode.setCmmnts(parentNode.getCmmnts() + 1);
            nodeService.save(parentNode);
        } else {
            PostEntity postEntity = postService.findPostById(commentResponse.getParentId());
            postEntity.setCmmnts(postEntity.getCmmnts() + 1);
            postService.createPost(postEntity);
        }

        return nodeService.save(node);
    }

    @RequestMapping("/add-new-post")
    public PostEntity addNewPost(@RequestBody NewPostResponse postResponse, Principal principal) {
        Date now = new Date(Calendar.getInstance().getTimeInMillis());

        PostEntity postEntity = new PostEntity();
        postEntity.setTitle(postResponse.getTitle());
        postEntity.setBody(postResponse.getBody());
        postEntity.setSubmit_date(now);
        postEntity.setLikes(0);
        postEntity.setCmmnts(0);
        postEntity.setUsername(principal.getName());

        return postService.createPost(postEntity);
    }

    @RequestMapping("/find-post/{postId}")
    public PostEntity findPost(@PathVariable int postId) {
        return postService.findPostById(postId);
    }

    //returns list of post entity objects for user sorted by likes desc
    @RequestMapping("/top-posts")
    public List<PostEntity> topPostsByUser(Principal principal) {
        List<PostEntity> fullList = postService.topPostsByUser(principal.getName());
        List<PostEntity> top15List = new ArrayList<>();
        int counterMax;

        if (fullList.size() < 15) {
            counterMax = fullList.size();
        } else {
            counterMax = 14;
        }

        for (int i = 0; i < counterMax; i++) {
            top15List.add(fullList.get(i));
        }

        return top15List;
    }

    @RequestMapping("/top-comment")
    public NodeEntity topComment(Principal principal) {
        Pageable topOne = new PageRequest(0,1);
        List<NodeEntity> nodeList = nodeService.findTopComment(principal.getName(), topOne);

        return nodeList.get(0);
    }

    @RequestMapping("/admin/query-node")
    public NodeEntity queryComment(@RequestBody NodeEntity nodeEntity) {
        return nodeService.findByBodyAndUsername(nodeEntity.getBody(), nodeEntity.getUsername());
    }

    @RequestMapping("/admin/get/{id}")
    public PostEntity queryPost(@PathVariable int id) {
        return postService.findPostById(id);
    }

    @RequestMapping("/admin/delete-comment")
    public AjaxResponse deleteComment(@RequestBody NodeEntity nodeEntity) {
        AjaxResponse response = new AjaxResponse();

        if (nodeService.exists(nodeEntity.getNodeId())) {
            nodeService.delete(nodeEntity.getNodeId());

            response.setCode("200");
            response.setMsg("Comment deleted.");

            return response;
        } else {
            response.setCode("404");
            response.setMsg("Comment does not exist.");

            return response;
        }
    }

    @RequestMapping("/register-user")
    public ResponseEntity<UserProfileEntity> register(@RequestBody UserProfileEntity payload, HttpServletRequest request) throws ServletException{

        //search db for user to avoid duplicate usernames
        UserProfileEntity user = userProfileService.findByUser(payload.getUsername());

        //if null add new user and login
        if (user == null) {
            RoleEntity role = new RoleEntity();
            role.setRole("ROLE_USER");

            Set<RoleEntity> roleSet = new HashSet<>();
            roleSet.add(role);

            UserProfileEntity userToAdd = new UserProfileEntity();
            userToAdd.setEnabled(true);
            userToAdd.setUsername(payload.getUsername());
            userToAdd.setPassword(payload.getPassword());
            userToAdd.setRoles(roleSet);

            userProfileService.save(userToAdd);

            request.login(userToAdd.getUsername(), userToAdd.getPassword());

            return new ResponseEntity<UserProfileEntity>(userToAdd, HttpStatus.CREATED);

        } else {
            return new ResponseEntity<UserProfileEntity>(HttpStatus.CONFLICT);
        }

    }
}
