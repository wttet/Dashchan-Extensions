package com.mishiranu.dashchan.chan.cirno;

import android.net.Uri;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import chan.content.model.FileAttachment;
import chan.content.model.Post;
import chan.content.model.Posts;
import chan.text.ParseException;
import chan.text.TemplateParser;
import chan.util.StringUtils;

public class CirnoCatalogParser {
	private final String source;
	private final CirnoChanLocator locator;

	private Post post;
	private final ArrayList<Posts> threads = new ArrayList<>();

	private static final Pattern LINK_TITLE = Pattern.compile("#(\\d+) \\((.*)\\)");

	public CirnoCatalogParser(String source, Object linked) {
		this.source = source;
		locator = CirnoChanLocator.get(linked);
	}

	public ArrayList<Posts> convert() throws ParseException {
		PARSER.parse(source, this);
		return threads;
	}

	private static final TemplateParser<CirnoCatalogParser> PARSER = new TemplateParser<CirnoCatalogParser>()
			.starts("a", "title", "#").open((instance, holder, tagName, attributes) -> {
		Matcher matcher = LINK_TITLE.matcher(attributes.get("title"));
		if (matcher.matches()) {
			String number = matcher.group(1);
			String date = matcher.group(2);
			Post post = new Post();
			post.setPostNumber(number);
			try {
				post.setTimestamp(CirnoPostsParser.DATE_FORMAT.parse(date).getTime());
			} catch (java.text.ParseException e) {
				// Ignore exception
			}
			holder.post = post;
			holder.threads.add(new Posts(post));
		}
		return false;
	}).name("img").open((instance, holder, tagName, attributes) -> {
		if (holder.post != null) {
			String src = attributes.get("src");
			if (src != null) {
				FileAttachment attachment = new FileAttachment();
				Uri thumbnailUri = src.contains("/thumb/") ? holder.locator.buildPath(src) : null;
				attachment.setThumbnailUri(holder.locator, thumbnailUri);
				attachment.setSpoiler(src.contains("extras/icons/spoiler.png"));
				if (thumbnailUri != null) {
					Uri fileUri = holder.locator.buildPath(src.replace("/thumb/", "/src/").replace("s.", "."));
					attachment.setFileUri(holder.locator, fileUri);
				}
				holder.post.setAttachments(attachment);
			}
		}
		return false;
	}).equals("span", "class", "filetitle").content((instance, holder, text) -> {
		if (holder.post != null) {
			holder.post.setSubject(StringUtils.nullIfEmpty(StringUtils.clearHtml(text).trim()));
		}
	}).equals("span", "class", "cattext").content((instance, holder, text) -> {
		if (holder.post != null) {
			text = StringUtils.nullIfEmpty(text);
			if (text != null) {
				text = text.trim() + '\u2026';
			}
			holder.post.setComment(text);
			holder.post = null;
		}
	}).prepare();
}